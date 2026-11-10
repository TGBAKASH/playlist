import React from 'react';

export default function Receipt({
  tracks,
  currentTrackId,
  isPlaying,
  onSelectTrack,
  searchQuery,
  onSearchChange,
  onPrint
}) {
  return (
    <div className="receipt-outer" id="printable-receipt">
      <div className="tape-element"></div>

      <article className="receipt-paper">
        <div className="receipt-header">
          <h2 className="stamped-title">HARRIS<br />JAYARAJ</h2>
        </div>

        <div className="receipt-search-box">
          <input
            type="text"
            placeholder="🔍 Search track or movie..."
            value={searchQuery}
            onChange={(e) => onSearchChange(e.target.value)}
            aria-label="Filter playlist tracks"
          />
        </div>

        <p className="receipt-tip"><em>👉 Click any song below to play live:</em></p>

        <div className="song-table">
          {tracks.map((track) => {
            const isCurrent = track.id === currentTrackId;
            return (
              <div
                key={track.id}
                className={`song-row ${isCurrent ? 'playing-row' : ''}`}
                onClick={() => onSelectTrack(track.id)}
              >
                <span className="song-name">
                  {isCurrent && isPlaying ? '▶ ' : ''}{track.title}
                </span>
                <span className="song-time">{track.duration}</span>
              </div>
            );
          })}
        </div>

        <hr className="receipt-divider" />

        <div className="receipt-summary">
          <div className="summary-line">
            <span>ITEM COUNT</span>
            <span>{tracks.length}</span>
          </div>
          <div className="summary-line">
            <span>TOTAL</span>
            <span>81:33</span>
          </div>
        </div>

        <hr className="receipt-divider" />

        <div className="receipt-footer">
          <div className="soundwave-code">
            <span className="b-line bh-1"></span>
            <span className="b-line bh-3"></span>
            <span className="b-line bh-2"></span>
            <span className="b-line bh-4"></span>
            <span className="b-line bh-1"></span>
            <span className="b-line bh-3"></span>
            <span className="b-line bh-4"></span>
            <span className="b-line bh-2"></span>
            <span className="b-line bh-3"></span>
            <span className="b-line bh-1"></span>
            <span className="b-line bh-4"></span>
            <span className="b-line bh-2"></span>
            <span className="b-line bh-3"></span>
            <span className="b-line bh-1"></span>
          </div>
          <p className="receipt-credit">RECEIPT BY ORICATESTEHTIC</p>
        </div>

        <div className="receipt-actions">
          <button className="btn-print" onClick={onPrint} title="Print receipt or save as PDF">
            🧾 Print / Save Receipt
          </button>
        </div>
      </article>
    </div>
  );
}
