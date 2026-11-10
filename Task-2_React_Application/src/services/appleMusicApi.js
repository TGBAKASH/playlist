/**
 * Service to query Apple's public iTunes Search API
 * Free, CORS-friendly, zero API key required
 */
const cache = {};

export async function fetchTrackPreview(track) {
  if (cache[track.id]) {
    return cache[track.id];
  }

  try {
    const url = `https://itunes.apple.com/search?term=${encodeURIComponent(track.query)}&entity=song&limit=1`;
    const response = await fetch(url);
    const data = await response.json();

    if (data.results && data.results.length > 0) {
      const item = data.results[0];
      const result = {
        previewUrl: item.previewUrl,
        artwork: item.artworkUrl100 ? item.artworkUrl100.replace('100x100bb', '300x300bb') : null,
        artistName: item.artistName || 'Harris Jayaraj',
        movieName: item.collectionName || track.movie
      };
      cache[track.id] = result;
      return result;
    }
  } catch (err) {
    console.error('Error fetching iTunes preview:', err);
  }

  return {
    previewUrl: null,
    artwork: null,
    artistName: 'Harris Jayaraj',
    movieName: track.movie
  };
}
