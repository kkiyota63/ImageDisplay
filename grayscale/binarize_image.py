import numpy as np
from PIL import Image
import matplotlib.pyplot as plt

def convert_to_grayscale(input_path):
    # 画像を開く
    img = Image.open(input_path)
    # グレースケールに変換
    return img.convert("L")

def binarize_image(grayscale_image, threshold):
    """画像を二値化する関数"""
    img_data = np.asarray(grayscale_image)
    binarized_data = np.where(img_data > threshold, 255, 0)
    return Image.fromarray(binarized_data.astype(np.uint8))

# 使用例
input_file_path = "/Users/kiyotakoki/dev/com_vis/grayscale/sakurajima.jpeg"

# グレースケール変換
grayscale_img = convert_to_grayscale(input_file_path)

# 二値化
threshold = 32
binarized_img = binarize_image(grayscale_img, threshold)

# 二値画像を表示
plt.figure(figsize=(5,5))
plt.imshow(binarized_img, cmap='gray')
plt.axis('off')  # 軸を非表示にする
plt.title(f'Binarized Image with Threshold = {threshold}')
plt.show()
