# Harris Jayaraj - Enhanced Discography & Receipt Web App 🎵

**College Term Work / Self-Learning Task 1 & 2 Submission**  
**Course**: Web Technology & Application Development  
**Assessment**: Continuous Learning Assessment – II (CLA–II)  
**Bloom’s Taxonomy Level**: **K2 (Understand)**, **K3 (Apply)**, **K4 (Analyze)**  

---

## 🌟 Advanced Project Features

### 1. Responsive Web Page Design (HTML5 & CSS3)
- **Semantic HTML5**: `<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<canvas>`, `<footer>`.
- **Modern CSS3 Layouts**: **CSS Grid** for macro layout & discography grid; **Flexbox** for micro components.
- **Responsive Media Queries**: Seamless adaptation across mobile (320px–480px), tablet (481px–900px), and wide desktop (1200px+).
- **Print Stylesheet (`@media print`)**: Hides UI chrome and prints only the authentic vintage receipt on clean paper.

### 2. Free Music API Integration (Apple iTunes Search API)
- **Zero Cost & No API Keys**: Calls Apple's public iTunes Search API to fetch 30-second studio previews and high-resolution album art.
- **CORS Compliant**: Apple's music CDN delivers audio with `Access-Control-Allow-Origin: *`, allowing the browser's Web Audio engine to analyze and amplify the stream.

### 3. Web Audio API Signal Processing Chain (200% Boost & Audio FX)
```
[HTML5 Audio] ──> [MediaElementSource]
                        │
                        ▼
             [BiquadFilterNode] (Bass Boost +8dB Low-Shelf at 180Hz)
                        │
                        ▼
                 [GainNode] (200% Volume Boost / +6dB Hardware Gain)
                        │
                        ▼
               [AnalyserNode] (FFT Frequency Bins for Spectrum)
                        │
                        ▼
              [audioCtx.destination] (Speakers / Headphones)
```
- **200% Volume Boost**: Overcomes standard browser `1.0` limit via `gainNode.gain.value = 2.0`.
- **🎧 Bass Boost Filter**: Low-shelf BiquadFilter targeting 180Hz with +8dB gain.
- **Live Canvas Spectrum Visualizer**: Renders dynamic animated frequency bars on an HTML5 `<canvas>` via `requestAnimationFrame`.

### 4. Search, Filter & Interactive Receipt
- **Real-time Live Search**: Instant filtering of the 16 songs in both the receipt and the discography cards.
- **Filter Chips**: One-click genre filtering ("All", "Suriya Hits", "Melodies", "2001-2005").
- **1-Click Receipt Playback**: Clicking any row on the paper receipt immediately streams that track with active row highlight.
- **Vinyl Spin Effect**: Rotating vinyl record artwork while audio plays.
- **Interactive Toast Notifications**: Instant feedback for volume changes and song selections.

### 5. Accessibility & Keyboard Shortcuts
- `Space`: Play / Pause
- `B`: Toggle 200% Super Boost
- `←` / `→`: Previous / Next Track
- `↑` / `↓`: Volume Up / Down (10% increments)
- `M`: Mute / Unmute

---

## 🎵 Complete Tracklist (16 Songs)

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

**ITEM COUNT**: 16  
**TOTAL DURATION**: 81:33  

---

## 🎓 Viva Voce / Presentation Defense Cheat Sheet

### 1. How did you implement 200% volume when HTML5 `<audio>` maxes out at 100%?
> **Answer**:  
> Standard HTML5 audio elements cap volume at `1.0`. We hooked the audio element into the browser's hardware-accelerated **Web Audio API** via `audioCtx.createMediaElementSource(audioElement)`. By routing the audio through a `GainNode` and setting `gainNode.gain.value = 2.0`, we achieve a $+6\text{ dB}$ hardware boost beyond physical browser limits.

### 2. How does the real-time audio visualizer work?
> **Answer**:  
> We instantiate an `AnalyserNode` connected to the gain node. Using `analyser.getByteFrequencyData()`, we extract real-time Fourier transform frequency magnitudes and paint them onto an HTML5 `<canvas>` inside a 60 FPS `requestAnimationFrame()` render loop.

### 3. How does the Bass Boost work?
> **Answer**:  
> We utilized a `BiquadFilterNode` configured as a `lowshelf` filter at $180\text{ Hz}$. When activated, it boosts low frequencies by $+8\text{ dB}$, enriching the bass response before hitting the gain node.

### 4. How does this map to Bloom's Taxonomy Levels?
> **Answer**:  
> - **K2 (Understand)**: HTML5 document tree semantics, CSS box model, responsive media query breakpoints.
> - **K3 (Apply)**: Applying CSS Grid, Flexbox, custom range styling, and DOM event handling.
> - **K4 (Analyze)**: Analyzing audio signal graphs in the Web Audio API (Source $\to$ Filter $\to$ Gain $\to$ Analyser) and real-time frequency bin distribution.

---

## 🚀 How to Run
1. Double-click [**`index.html`**](file:///c:/Users/tgbak/Playlist/index.html) to open the application in any modern browser.
2. Click any track on the receipt or use keyboard shortcuts (`Space` to play, `B` to toggle 200% boost).
3. Try the live search bar or filter chips to filter songs in real-time.
4. Click **"🧾 Print / Save Receipt"** to print or export the vintage receipt!
