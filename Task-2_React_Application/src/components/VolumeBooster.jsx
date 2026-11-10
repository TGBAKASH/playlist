import React from 'react';

export default function VolumeBooster({
  volume,
  onVolumeChange,
  onMaxBoost,
  isBassBoost,
  onToggleBassBoost
}) {
  return (
    <div className="volume-booster-panel">
      <div className="volume-header">
        <span className="vol-label">🔊 Master Volume & Audio FX</span>
        <div className="boost-tags">
          <button
            className={`btn-fx ${isBassBoost ? 'active' : ''}`}
            onClick={onToggleBassBoost}
            title="Toggle Bass Boost Low-Shelf Filter"
          >
            🎧 BASS BOOST: {isBassBoost ? 'ON (+8dB)' : 'OFF'}
          </button>
          <span className={`boost-pill ${volume > 100 ? 'active' : ''}`}>
            {volume > 100 ? `⚡ BOOST: ${volume}%` : `${volume}% STANDARD`}
          </span>
          <button
            className="btn-boost-max"
            onClick={onMaxBoost}
            title="Instantly amplify to 200% volume"
          >
            ⚡ 200% BOOST
          </button>
        </div>
      </div>

      <div className="volume-slider-wrapper">
        <input
          type="range"
          min="0"
          max="200"
          value={volume}
          onChange={(e) => onVolumeChange(Number(e.target.value))}
          className="volume-slider"
          aria-label="Volume Slider 0 to 200 percent"
        />
        <div className="slider-marks">
          <span>0%</span>
          <span>50%</span>
          <span>100% (Std)</span>
          <span className="mark-boost">200% (Boost)</span>
        </div>
      </div>

      <div className="keyboard-hints">
        <span>⌨️ Shortcuts: <code>Space</code> Play/Pause • <code>B</code> 200% Boost • <code>← / →</code> Prev/Next • <code>↑ / ↓</code> Volume</span>
      </div>
    </div>
  );
}
