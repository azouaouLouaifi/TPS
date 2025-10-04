#pragma once 
#include "fonction.hpp"

// Fonction pour créer la LUT à partir de l'histogramme cumulé
void creerLUT(const vector<int>& histogrammeCumule, vector<int>& LUT, int totalPixels) {
    int Cmin = *min_element(histogrammeCumule.begin(), histogrammeCumule.end(),
                            [](int a, int b) { return (a > 0) && (b == 0 || a < b); });
                            
    for (int i = 0; i < 256; i++) {
        LUT[i] = cvRound(((double)(histogrammeCumule[i] - Cmin) / (totalPixels - Cmin)) * 255);
    }
}
