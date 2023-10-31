import cv2

#macOS
capture = cv2.VideoCapture(0)
#Windows
#capture = cv2.VideoCapture(0, cv2.CAP_DSHOW)

#名前付きWindowにCallback関数を設定
cv2.namedWindow('frame')

#white (capture.isOpened()):
while (True):
    ret, frame = capture.read()
    cv2.imshow('frame', frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

capture.release()
cv2.destroyAllWindows()