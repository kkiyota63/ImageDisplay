import numpy as np
from PIL import Image
import matplotlib.pyplot as plt

def display_grayscale_image_and_histogram(input_path):
    # 画像を開く
    img = Image.open(input_path)
    
    # グレースケールに変換
    grayscale_img = img.convert("L")
    
    # 画像のデータをnumpy配列に変換
    img_data = np.asarray(grayscale_img)

    # グレースケール画像を表示
    plt.figure(figsize=(10,5))

    # 左側にグレースケール画像を表示
    plt.subplot(1, 2, 1)
    plt.imshow(grayscale_img, cmap='gray')
    plt.axis('off')  # 軸を非表示にする
    plt.title('Grayscale Image')

    # 右側にヒストグラムを表示
    plt.subplot(1, 2, 2)
    plt.hist(img_data.ravel(), bins=256, range=(0,256), density=True, color='gray', alpha=0.7)
    plt.xlabel('Pixel Value')
    plt.ylabel('Frequency')
    plt.title('Histogram of Grayscale Image')
    plt.grid(axis='y', linestyle='--')

    plt.tight_layout()  # レイアウトをきれいにする
    plt.show()

# 使用例
input_file_path = "/Users/kiyotakoki/dev/com_vis/grayscale/sakurajima.jpeg"

display_grayscale_image_and_histogram(input_file_path)
