# -*- coding: utf-8 -*-

import numpy as np
import cv2
from matplotlib import pyplot as plt

# 画像をグレースケールで読み込む
img = cv2.imread('/Users/kiyotakoki/dev/com_vis/07_DFT/sakurajima.jpeg',0)

# DFT
dft = cv2.dft(np.float32(img),flags = cv2.DFT_COMPLEX_OUTPUT)
# 結果をシフト
dft_shift = np.fft.fftshift(dft)

# 見易いスケールに変換
magnitude_spectrum = 20*np.log(cv2.magnitude(dft_shift[:,:,0],dft_shift[:,:,1]))

plt.subplot(121),plt.imshow(img, cmap = 'gray')
plt.title('Input Image'), plt.xticks([]), plt.yticks([])
plt.subplot(122),plt.imshow(magnitude_spectrum, cmap = 'gray')
plt.title('Magnitude Spectrum'), plt.xticks([]), plt.yticks([])
plt.show()

cv2.imwrite("magnitude_spectrum.png", magnitude_spectrum)

