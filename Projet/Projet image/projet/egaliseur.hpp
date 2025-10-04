#pragma once 


// fonction egalisation 
Mat egaliserHistogramme(const vector<vector<int>>& imageMatrix) {
    int rows = (int)imageMatrix.size();
    int cols = (int)imageMatrix[0].size();
    int totalPixels = rows * cols;

    //  Calculer l'histogramme des niveaux de gris
    vector<int> histogramme(256, 0);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int intensity = imageMatrix[i][j] & 0xFF; 
            histogramme[intensity]++;
        }
    }
    //  Calculer l'histogramme cumulé normalisé
    vector<int> histogrammeCumule(256, 0);
    histogrammeCumule[0] = histogramme[0];
    for (int i = 1; i < 256; i++) {
        histogrammeCumule[i] = histogrammeCumule[i - 1] + histogramme[i];
    }
    // Normaliser l'histogramme cumulé 
    vector<int> lut(256, 0);
    for (int i = 0; i < 256; i++) {
        lut[i] = (histogrammeCumule[i] * 255) / totalPixels; 
    }

    // Appliquer la LUT pour créer l'image égalisée
    vector<vector<int>> equalizedMatrix(rows, vector<int>(cols));
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int oldIntensity = imageMatrix[i][j] & 0xFF;
            int newIntensity = lut[oldIntensity];
            equalizedMatrix[i][j] = rgbToInt(newIntensity, newIntensity, newIntensity);
        }
    }
    return convertImageMat(equalizedMatrix);
}
