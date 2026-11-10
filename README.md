# Harris Jayaraj - Master 8D Audio & Viral Receipt Platform 🎵

**College Term Work / Self-Learning Assessment (CLA–II)**  
**Course**: Web Technology & Application Development  
**Bloom’s Taxonomy**: **K2 (Understand)**, **K3 (Apply)**, **K4 (Analyze)**, **K6 (Create)**  

---

## 🌟 Jaw-Dropping WOW Features

### 1. 🎧 8D Spatial Audio Orbital Panner (Web Audio API)
- Connects audio to a hardware `StereoPannerNode`.
- Uses an orbital Low-Frequency Oscillator (LFO: $\sin(\theta)$) to pan sound around the listener's head in a 3D circle between left and right channels.
- Includes a live orbital radar indicator!

### 2. 🎛️ 5-Band Studio Graphic Equalizer (Digital Signal Processing)
- 5 individual `BiquadFilterNode`s:
  - **60Hz (Sub)**: Low-shelf filter for deep sub-bass punch.
  - **250Hz (Bass)**: Peaking filter for warmth.
  - **1kHz (Mid)**: Peaking filter for instruments and guitars.
  - **4kHz (Presence)**: Peaking filter for vocal clarity.
  - **12kHz (Treble)**: High-shelf filter for air and sparkle.
- Includes 5 studio presets: *Studio Flat, Bass Heavy (+10dB), Vocal Clarity, Treble Sparkle, Concert Hall*.

### 3. 📸 Instant High-Res PNG Receipt Exporter
- Client-side HTML5 Canvas generator renders the receipt graphic (title, 16 songs, times, dashed lines, barcode) and automatically triggers a direct download of `harris-jayaraj-receipt.png` to share on social media!

### 4. 📜 Synchronized Lyrics Drawer
- Integrated Tamil & English lyrics for Harris Jayaraj's hit tracks (*Mun Andhi*, *Mazhai Vara Pogudhae*, *Suttum Vizhi*, etc.) with glowing active line highlighting.

### 5. 📊 3-Mode Canvas Visualizer
- **Bars**: Multi-color frequency spectrum bins.
- **Wave**: Real-time oscilloscope time-domain waveform (`getByteTimeDomainData`).
- **Pulse**: Audio-reactive glowing beat rings that pulse to the bass drop!

### 6. ⚡ 200% Hardware Volume Boost
- Web Audio `GainNode` configured to `2.0` (+6dB gain) overcoming the browser's 100% native audio ceiling.

---

## 🎵 Complete 16-Song Tracklist

| # | Track Name | Original Motion Picture | Year | Duration |
|---|---|---|---|---|
| 01 | **MUN ANDHI** | *7 Aum Arivu* | 2011 | 6:14 |
| 02 | **MAZHAI VARA POGUDHAE** | *Yennai Arindhaal* | 2015 | 5:42 |
| 03 | **PUYALAE PUYALAE** | *Kovil* | 2004 | 4:58 |
| 04 | **ONDRA RENDA** | *Kaakha Kaakha* | 2003 | 5:06 |
| 05 | **RAGASIYA KANAVUGAL** | *Bheema* | 2008 | 5:59 |
| 06 | **THEE ILLAI** | *Engeyum Kadhal* | 2011 | 5:27 |
| 07 | **LESA LESA** | *Lesa Lesa* | 2003 | 4:41 |
| 08 | **EN FRIENDA POLA** | *Nanban* | 2012 | 3:57 |
| 09 | **SUTTUM VIZHI** | *Ghajini* | 2005 | 5:18 |
| 10 | **ENNAMO YEDHO** | *Ko* | 2011 | 5:34 |
| 11 | **UNNAALE UNNAALE** | *Unnale Unnale* | 2007 | 4:43 |
| 12 | **EN ANBE** | *Sathyam* | 2008 | 6:07 |
| 13 | **AATHANGARA ORATHIL** | *Yaan* | 2014 | 4:24 |
| 14 | **PAARTHA MUDHAL** | *Vettaiyaadu Vilaiyaadu* | 2006 | 6:08 |
| 15 | **AVA ENNA** | *Vaaranam Aayiram* | 2008 | 5:18 |
| 16 | **POOPOL POOPOL** | *Minnalae* | 2001 | 1:57 |

---

## ⌨️ Global Keyboard Shortcuts
- `Space`: Play / Pause
- `B`: Toggle 200% Super Boost
- `S`: Toggle 8D Spatial Audio Panning
- `L`: Open Song Lyrics Drawer
- `E`: Open 5-Band Studio Graphic EQ
- `←` / `→`: Previous / Next track
- `↑` / `↓`: Volume Up / Down (10% increments)

---

## 🚀 How to Run
Double-click [**`index.html`**](file:///c:/Users/tgbak/Playlist/index.html) to open in any browser!
