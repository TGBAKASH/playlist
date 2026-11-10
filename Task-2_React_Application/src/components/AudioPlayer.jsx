import React, { useRef, useEffect } from 'react';
import VolumeBooster from './VolumeBooster';

export default function AudioPlayer({
  track,
  artwork,
  isPlaying,
  onTogglePlay,
  onPrev,
  onNext,
  progress,
  currentTime,
  duration,
  onSeek,
  volume,
  onVolumeChange,
  onMaxBoost,
  isBassBoost,
  onToggleBassBoost,
  analyserNode
}) {
  const canvasRef = useRef(null);

  // Real-time Spectrum Visualizer loop
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    let animationId;

    const bufferLength = analyserNode ? analyserNode.frequencyBinCount : 32;
    const dataArray = new Uint8Array(bufferLength);

    const render = () => {
      animationId = requestAnimationFrame(render);
      const width = canvas.width;
      const height = canvas.height;
      ctx.clearRect(0, 0, width, height);

      if (isPlaying && analyserNode) {
        analyserNode.getByteFrequencyData(dataArray);
      } else {
        for (let i = 0; i < bufferLength; i++) {
          dataArray[i] = Math.max(6, dataArray[i] * 0.9);
        }
      }

      const barWidth = (width / bufferLength) * 1.6;
      let x = 0;

      for (let i = 0; i < bufferLength; i++) {
        const barHeight = (dataArray[i] / 255) * height * 0.95;
        const gradient = ctx.createLinearGradient(0, height, 0, height - barHeight);
        gradient.addColorStop(0, '#10b981');
        gradient.addColorStop(0.6, '#f59e0b');
        gradient.addColorStop(1, '#ff3366');

        ctx.fillStyle = gradient;
        ctx.fillRect(x, height - barHeight, barWidth - 2, barHeight);
        x += barWidth;
      }
    };

    render();
    return () => cancelAnimationFrame(animationId);
  }, [isPlaying, analyserNode]);

  return (
    <article className="player-card">
      <div className="player-top">
        <div className={`album-art-wrapper ${isPlaying ? 'spinning' : ''}`}>
          <img
            src={artwork || 'https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/a4/c1/95/a4c19595-7c67-3f73-1322-83569e5950d2/mzi.bopmwhba.jpg/300x300bb.jpg'}
            alt="Album Cover"
            className="album-cover-img"
          />
          <div className="vinyl-center-hole"></div>
        </div>

        <div className="track-details">
          <div className="status-row">
            <span className="now-playing-label">NOW PLAYING</span>
            <div className={`equalizer ${isPlaying ? 'playing' : ''}`}>
              <span className="eq-bar"></span>
              <span className="eq-bar"></span>
              <span className="eq-bar"></span>
              <span className="eq-bar"></span>
            </div>
          </div>
          <h3 className="track-title">{track ? track.title : 'Loading...'}</h3>
          <p className="track-artist">
            Harris Jayaraj • {track ? `${track.movie} (${track.year})` : ''}
          </p>
        </div>
      </div>

      <div className="visualizer-wrapper">
        <canvas ref={canvasRef} className="visualizer-canvas" width={400} height={46}></canvas>
      </div>

      <div className="progress-section">
        <div
          className="progress-bar-container"
          onClick={(e) => {
            const rect = e.currentTarget.getBoundingClientRect();
            const pct = (e.clientX - rect.left) / rect.width;
            onSeek(pct);
          }}
          title="Click to seek"
        >
          <div className="progress-fill" style={{ width: `${progress}%` }}></div>
        </div>
        <div className="time-display">
          <span>{currentTime}</span>
          <span>{duration}</span>
        </div>
      </div>

      <div className="controls-row">
        <button className="btn-ctrl" onClick={onPrev} title="Previous Track (←)">⏮</button>
        <button className="btn-ctrl btn-play" onClick={onTogglePlay} title="Play/Pause (Space)">
          {isPlaying ? '⏸' : '▶'}
        </button>
        <button className="btn-ctrl" onClick={onNext} title="Next Track (→)">⏭</button>
      </div>

      <VolumeBooster
        volume={volume}
        onVolumeChange={onVolumeChange}
        onMaxBoost={onMaxBoost}
        isBassBoost={isBassBoost}
        onToggleBassBoost={onToggleBassBoost}
      />
    </article>
  );
}
