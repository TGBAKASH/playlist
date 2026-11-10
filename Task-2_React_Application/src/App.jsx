import React, { useState, useEffect, useRef, useCallback } from 'react';
import AudioPlayer from './components/AudioPlayer';
import Receipt from './components/Receipt';
import DiscographyGrid from './components/DiscographyGrid';
import { fetchTrackPreview } from './services/appleMusicApi';
import './styles/App.css';

const MASTER_TRACKS = [
  { id: 0, title: "MUN ANDHI", query: "Mun Andhi Harris Jayaraj", movie: "7 Aum Arivu", year: "2011", duration: "6:14", tags: ["surya", "romantic"] },
  { id: 1, title: "MAZHAI VARA POGUDHAE", query: "Mazhai Vara Pogudhae", movie: "Yennai Arindhaal", year: "2015", duration: "5:42", tags: ["romantic"] },
  { id: 2, title: "PUYALAE PUYALAE", query: "Puyale Puyale", movie: "Kovil", year: "2004", duration: "4:58", tags: ["classic"] },
  { id: 3, title: "ONDRA RENDA", query: "Ondra Renda", movie: "Kaakha Kaakha", year: "2003", duration: "5:06", tags: ["surya", "classic", "romantic"] },
  { id: 4, title: "RAGASIYA KANAVUGAL", query: "Ragasiya Kanavugal", movie: "Bheema", year: "2008", duration: "5:59", tags: ["romantic"] },
  { id: 5, title: "THEE ILLAI", query: "Thee Illai", movie: "Engeyum Kadhal", year: "2011", duration: "5:27", tags: ["romantic"] },
  { id: 6, title: "LESA LESA", query: "Lesa Lesa Harris Jayaraj", movie: "Lesa Lesa", year: "2003", duration: "4:41", tags: ["classic", "romantic"] },
  { id: 7, title: "EN FRIENDA POLA", query: "En Frienda Pola", movie: "Nanban", year: "2012", duration: "3:57", tags: ["classic"] },
  { id: 8, title: "SUTTUM VIZHI", query: "Suttum Vizhi", movie: "Ghajini", year: "2005", duration: "5:18", tags: ["surya", "classic", "romantic"] },
  { id: 9, title: "ENNAMO YEDHO", query: "Ennamo Yeadho", movie: "Ko", year: "2011", duration: "5:34", tags: ["romantic"] },
  { id: 10, title: "UNNAALE UNNAALE", query: "Unnale Unnale", movie: "Unnale Unnale", year: "2007", duration: "4:43", tags: ["romantic"] },
  { id: 11, title: "EN ANBE", query: "En Anbe Sathyam", movie: "Sathyam", year: "2008", duration: "6:07", tags: ["romantic"] },
  { id: 12, title: "AATHANGARA ORATHIL", query: "Aathangara Orathil", movie: "Yaan", year: "2014", duration: "4:24", tags: ["romantic"] },
  { id: 13, title: "PAARTHA MUDHAL", query: "Paartha Mudhal", movie: "Vettaiyaadu Vilaiyaadu", year: "2006", duration: "6:08", tags: ["romantic"] },
  { id: 14, title: "AVA ENNA", query: "Ava Enna Vaaranam Aayiram", movie: "Vaaranam Aayiram", year: "2008", duration: "5:18", tags: ["surya", "romantic"] },
  { id: 15, title: "POOPOL POOPOL", query: "Poopol Poopol", movie: "Minnalae", year: "2001", duration: "1:57", tags: ["classic", "romantic"] }
];

export default function App() {
  const [currentTrackIndex, setCurrentTrackIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(100);
  const [isBassBoost, setIsBassBoost] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [artwork, setArtwork] = useState(null);
  const [progress, setProgress] = useState(0);
  const [currentTime, setCurrentTime] = useState('0:00');
  const [duration, setDuration] = useState('0:30');
  const [toastMessage, setToastMessage] = useState(null);

  const audioRef = useRef(null);
  const audioCtxRef = useRef(null);
  const gainNodeRef = useRef(null);
  const bassFilterRef = useRef(null);
  const analyserNodeRef = useRef(null);
  const sourceNodeRef = useRef(null);

  const currentTrack = MASTER_TRACKS[currentTrackIndex];

  // Helper toast
  const triggerToast = (msg) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(null), 2500);
  };

  // Setup Web Audio API
  const initAudio = useCallback(() => {
    if (!audioCtxRef.current) {
      const AudioCtxClass = window.AudioContext || window.webkitAudioContext;
      const ctx = new AudioCtxClass();
      audioCtxRef.current = ctx;

      const source = ctx.createMediaElementSource(audioRef.current);
      sourceNodeRef.current = source;

      const bass = ctx.createBiquadFilter();
      bass.type = 'lowshelf';
      bass.frequency.setValueAtTime(180, ctx.currentTime);
      bass.gain.setValueAtTime(isBassBoost ? 8 : 0, ctx.currentTime);
      bassFilterRef.current = bass;

      const gain = ctx.createGain();
      gain.gain.setValueAtTime(volume / 100, ctx.currentTime);
      gainNodeRef.current = gain;

      const analyser = ctx.createAnalyser();
      analyser.fftSize = 64;
      analyser.smoothingTimeConstant = 0.8;
      analyserNodeRef.current = analyser;

      source.connect(bass);
      bass.connect(gain);
      gain.connect(analyser);
      analyser.connect(ctx.destination);
    }

    if (audioCtxRef.current.state === 'suspended') {
      audioCtxRef.current.resume();
    }
  }, [isBassBoost, volume]);

  // Handle Track Selection
  const selectTrack = useCallback(async (index, autoPlay = true) => {
    setCurrentTrackIndex(index);
    const track = MASTER_TRACKS[index];
    const data = await fetchTrackPreview(track);

    if (data.artwork) setArtwork(data.artwork);

    if (data.previewUrl && audioRef.current) {
      audioRef.current.src = data.previewUrl;
      audioRef.current.load();

      if (autoPlay) {
        initAudio();
        audioRef.current.play()
          .then(() => {
            setIsPlaying(true);
            triggerToast(`▶ Now Playing: ${track.title}`);
          })
          .catch((e) => console.log('Autoplay prevented:', e));
      }
    }
  }, [initAudio]);

  // Initial Preload
  useEffect(() => {
    selectTrack(0, false);
  }, [selectTrack]);

  // Volume Adjustment
  const handleVolumeChange = (newVol) => {
    initAudio();
    setVolume(newVol);
    if (gainNodeRef.current && audioCtxRef.current) {
      gainNodeRef.current.gain.setTargetAtTime(newVol / 100, audioCtxRef.current.currentTime, 0.05);
    }
    if (newVol === 200) {
      triggerToast('⚡ 200% SUPER BOOST ACTIVATED (+6dB)!');
    }
  };

  // Bass Boost Toggle
  const handleToggleBassBoost = () => {
    initAudio();
    const nextState = !isBassBoost;
    setIsBassBoost(nextState);
    if (bassFilterRef.current && audioCtxRef.current) {
      bassFilterRef.current.gain.setTargetAtTime(nextState ? 8 : 0, audioCtxRef.current.currentTime, 0.08);
    }
    triggerToast(`🎧 Bass Boost: ${nextState ? 'ON (+8dB)' : 'OFF'}`);
  };

  // Play / Pause
  const togglePlay = () => {
    initAudio();
    if (!audioRef.current.src) {
      selectTrack(currentTrackIndex, true);
      return;
    }
    if (audioRef.current.paused) {
      audioRef.current.play();
      setIsPlaying(true);
    } else {
      audioRef.current.pause();
      setIsPlaying(false);
    }
  };

  const handlePrev = () => {
    const prev = (currentTrackIndex - 1 + MASTER_TRACKS.length) % MASTER_TRACKS.length;
    selectTrack(prev, true);
  };

  const handleNext = () => {
    const next = (currentTrackIndex + 1) % MASTER_TRACKS.length;
    selectTrack(next, true);
  };

  const handleSeek = (pct) => {
    if (audioRef.current && audioRef.current.duration) {
      audioRef.current.currentTime = pct * audioRef.current.duration;
    }
  };

  // Keyboard Shortcuts
  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.target.tagName === 'INPUT') return;
      if (e.code === 'Space') {
        e.preventDefault();
        togglePlay();
      } else if (e.code === 'ArrowRight') {
        e.preventDefault();
        handleNext();
      } else if (e.code === 'ArrowLeft') {
        e.preventDefault();
        handlePrev();
      } else if (e.code === 'KeyB') {
        e.preventDefault();
        handleVolumeChange(volume === 200 ? 100 : 200);
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [togglePlay, handleNext, handlePrev, volume]);

  // Filtered tracks
  const filteredTracks = MASTER_TRACKS.filter((t) => {
    const q = searchQuery.toLowerCase().trim();
    const matchesQuery = !q || t.title.toLowerCase().includes(q) || t.movie.toLowerCase().includes(q);
    const matchesCat = selectedCategory === 'all' || (t.tags && t.tags.includes(selectedCategory));
    return matchesQuery && matchesCat;
  });

  return (
    <div className="react-app-wrapper">
      <audio
        ref={audioRef}
        crossOrigin="anonymous"
        onTimeUpdate={() => {
          if (audioRef.current && audioRef.current.duration) {
            const cur = audioRef.current.currentTime;
            const dur = audioRef.current.duration;
            setProgress((cur / dur) * 100);

            const cm = Math.floor(cur / 60);
            const cs = Math.floor(cur % 60).toString().padStart(2, '0');
            setCurrentTime(`${cm}:${cs}`);

            const dm = Math.floor(dur / 60);
            const ds = Math.floor(dur % 60).toString().padStart(2, '0');
            setDuration(`${dm}:${ds}`);
          }
        }}
        onEnded={handleNext}
      />

      {toastMessage && (
        <div className="toast-notification show boost-toast">{toastMessage}</div>
      )}

      <header className="navbar">
        <div className="nav-brand">
          <span className="brand-icon">🎵</span>
          <span className="brand-name">Vibzz React Studio</span>
        </div>
        <div className="nav-tags">
          <span className="api-badge">🟢 Apple API Connected</span>
          <span className="boost-header-badge">⚡ React 18 + Web Audio</span>
          <span className="course-badge">CLA–II Task 2 (K3, K4)</span>
        </div>
      </header>

      <main className="main-container">
        <section className="player-column">
          <div className="hero-badge">React 18 Audio Platform</div>
          <h1 className="artist-title">HARRIS <span>JAYARAJ</span></h1>
          <p className="artist-desc">
            An interactive single-page ReactJS music platform showcasing modular components, custom hooks, real-time audio spectrum visualization, and <strong>200% Web Audio gain amplification</strong>.
          </p>

          <div className="stats-grid">
            <div className="stat-card">
              <span className="stat-value">{MASTER_TRACKS.length}</span>
              <span className="stat-label">Tracks</span>
            </div>
            <div className="stat-card">
              <span className="stat-value">81:33</span>
              <span className="stat-label">Duration</span>
            </div>
            <div className="stat-card">
              <span className="stat-value">200%</span>
              <span className="stat-label">Max Gain</span>
            </div>
          </div>

          <AudioPlayer
            track={currentTrack}
            artwork={artwork}
            isPlaying={isPlaying}
            onTogglePlay={togglePlay}
            onPrev={handlePrev}
            onNext={handleNext}
            progress={progress}
            currentTime={currentTime}
            duration={duration}
            onSeek={handleSeek}
            volume={volume}
            onVolumeChange={handleVolumeChange}
            onMaxBoost={() => handleVolumeChange(200)}
            isBassBoost={isBassBoost}
            onToggleBassBoost={handleToggleBassBoost}
            analyserNode={analyserNodeRef.current}
          />
        </section>

        <section className="receipt-column">
          <Receipt
            tracks={filteredTracks}
            currentTrackId={currentTrackIndex}
            isPlaying={isPlaying}
            onSelectTrack={(id) => selectTrack(id, true)}
            searchQuery={searchQuery}
            onSearchChange={setSearchQuery}
            onPrint={() => window.print()}
          />
        </section>
      </main>

      <DiscographyGrid
        tracks={filteredTracks}
        currentTrackId={currentTrackIndex}
        onSelectTrack={(id) => selectTrack(id, true)}
        selectedCategory={selectedCategory}
        onSelectCategory={setSelectedCategory}
      />

      <footer className="site-footer">
        <p>Web Technology Term Work • Task 2: Interactive JavaScript and ReactJS Application Development</p>
        <p className="sub-credit">Bloom's Taxonomy Level K3 & K4 • Componentized React Architecture with Web Audio API Integration</p>
      </footer>
    </div>
  );
}
