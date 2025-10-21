# Publishing to JetBrains Marketplace

## Schritt 1: JetBrains Account & Plugin registrieren

1. Gehe zu https://plugins.jetbrains.com/
2. Melde dich mit deinem JetBrains Account an (oder erstelle einen)
3. Klicke auf **"Upload plugin"** → **"Get started"**
4. Fülle das Formular aus:
   - **Plugin name**: Make Targets
   - **Plugin ID**: `de.brainbo.make-targets` (aus plugin.xml)
   - **License**: Wähle eine Lizenz (z.B. MIT)
   - **Category**: Build & Deployment Tools

## Schritt 2: Token generieren

1. Gehe zu https://plugins.jetbrains.com/author/me/tokens
2. Erstelle einen neuen **Permanent Token**
3. Kopiere den Token (du siehst ihn nur einmal!)

## Schritt 3: Plugin signieren (Optional, aber empfohlen)

```bash
# Certificate Chain generieren
# Dies ist optional für das erste Release
# Siehe: https://plugins.jetbrains.com/docs/intellij/plugin-signing.html
```

## Schritt 4: Plugin veröffentlichen

### Option A: Manuelles Upload (Einfachste Methode)

1. Build das Plugin:
   ```bash
   ./gradlew buildPlugin
   ```

2. Die ZIP-Datei findest du hier:
   ```
   build/distributions/make-targets-plugin.zip
   ```

3. Gehe zu https://plugins.jetbrains.com/plugin/add
4. Lade die ZIP-Datei hoch
5. Fülle die Informationen aus (werden aus plugin.xml übernommen)
6. Klicke auf **"Upload"**

### Option B: Automatisches Publishing via Gradle

1. Setze die Umgebungsvariable für den Token:
   ```bash
   export PUBLISH_TOKEN="dein-token-hier"
   ```

2. Veröffentliche das Plugin:
   ```bash
   ./gradlew publishPlugin
   ```

### Option C: Via GitHub Actions (CI/CD)

Erstelle `.github/workflows/publish.yml`:

```yaml
name: Publish Plugin

on:
  release:
    types: [created]

jobs:
  publish:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Publish Plugin
        env:
          PUBLISH_TOKEN: ${{ secrets.PUBLISH_TOKEN }}
        run: ./gradlew publishPlugin
```

Dann:
1. Füge deinen Token als Secret hinzu: https://github.com/DEIN-USER/DEIN-REPO/settings/secrets/actions
2. Erstelle einen neuen Release auf GitHub
3. Das Plugin wird automatisch veröffentlicht

## Schritt 5: Review-Prozess

Nach dem Upload:
1. JetBrains prüft das Plugin (kann 1-3 Werktage dauern)
2. Du erhältst eine E-Mail über das Ergebnis
3. Bei Genehmigung wird das Plugin veröffentlicht

## Wichtige Hinweise

- **Erste Veröffentlichung**: Kann länger dauern (1-5 Tage Review)
- **Updates**: Schneller, meist 1-2 Tage
- **Plugin-ID**: Kann nach der ersten Veröffentlichung NICHT mehr geändert werden!
- **Versionierung**: Verwende Semantic Versioning (0.1.0, 0.2.0, 1.0.0, etc.)

## Updates veröffentlichen

1. Erhöhe die Version in `build.gradle.kts`:
   ```kotlin
   version = "0.2.0"
   ```

2. Aktualisiere die Change Notes in `plugin.xml`

3. Build & Upload (Option A) oder `./gradlew publishPlugin` (Option B)

## Hilfreich Links

- Plugin Portal: https://plugins.jetbrains.com/
- Token Management: https://plugins.jetbrains.com/author/me/tokens
- Dokumentation: https://plugins.jetbrains.com/docs/marketplace/
- Plugin Signing: https://plugins.jetbrains.com/docs/intellij/plugin-signing.html
