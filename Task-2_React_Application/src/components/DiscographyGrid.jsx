import React from 'react';

export default function DiscographyGrid({
  tracks,
  currentTrackId,
  onSelectTrack,
  selectedCategory,
  onSelectCategory
}) {
  return (
    <section className="discography-section" id="discography" aria-label="Discography Catalog">
      <div className="discography-container">
        <div className="section-head">
          <div>
            <h2>Featured Movie Soundtracks</h2>
            <p>Click any soundtrack card below to preview the original master recording</p>
          </div>
          <div className="filter-chips">
            <button
              className={`chip ${selectedCategory === 'all' ? 'active' : ''}`}
              onClick={() => onSelectCategory('all')}
            >
              All (16)
            </button>
            <button
              className={`chip ${selectedCategory === 'surya' ? 'active' : ''}`}
              onClick={() => onSelectCategory('surya')}
            >
              Suriya Hits
            </button>
            <button
              className={`chip ${selectedCategory === 'romantic' ? 'active' : ''}`}
              onClick={() => onSelectCategory('romantic')}
            >
              Melodies
            </button>
            <button
              className={`chip ${selectedCategory === 'classic' ? 'active' : ''}`}
              onClick={() => onSelectCategory('classic')}
            >
              2001-2005
            </button>
          </div>
        </div>

        <div className="disc-grid">
          {tracks.map((track) => {
            const isCurrent = track.id === currentTrackId;
            return (
              <div
                key={track.id}
                className={`disc-card ${isCurrent ? 'active-card' : ''}`}
                onClick={() => onSelectTrack(track.id)}
              >
                <span className="disc-num">{String(track.id + 1).padStart(2, '0')}</span>
                <div className="disc-meta">
                  <h4>{track.title}</h4>
                  <p>{track.movie} • {track.year}</p>
                </div>
                <span className="disc-play-btn">{isCurrent ? '⏸' : '▶'}</span>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
