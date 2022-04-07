// package com.fluttertelpo.flutter_telpo

// import android.content.res.AssetManager
// import android.graphics.Bitmap
// import android.graphics.BitmapFactory
// import android.os.*
// import com.google.zxing.BarcodeFormat
// import com.google.zxing.MultiFormatWriter
// import com.google.zxing.WriterException
// import com.google.zxing.common.BitMatrix
// import com.telpo.tps550.api.printer.ThermalPrinter

// import java.io.InputStream
// import java.util.*


// class ThermalPrinterClass {
//     private val NOPAPER = 3
//     private val LOWBATTERY = 4
//     private val PRINTVERSION = 5
//     private val PRINTBARCODE = 6
//     private val PRINTQRCODE = 7
//     private val PRINTPAPERWALK = 8
//     private val PRINTCONTENT = 9
//     private val CANCELPROMPT = 10
//     private val PRINTERR = 11
//     private val OVERHEAT = 12
//     private val MAKER = 13
//     private val PRINTPICTURE = 14
//     private val NOBLACKBLOCK = 15
//     private var nopaper = false
//     private var LowBattery = false
//     private var leftDistance = 0
//     private var lineDistance = 0
//     private var wordFont = 0
//     private var printGray = 7
//     private var charSpace = 0
//     private var fontSize: Int = 2;
//     private var textAlignment: Int = ThermalPrinter.ALGIN_LEFT;
//     private var Result: String? = null
//     lateinit var mainActivity: FlutterTelpoPlugin
// //    var mThermalPrinter: ThermalPrinter? = null;
//     private var printVersion: String? = null
//     var barcodeStr: String? = null
//     var qrcodeStr: String? = null
//     var paperWalk = 0
//     var printContent: String ?= null
//     private var documentDetails: MutableList<Map<String, Any>> = ArrayList();
//     private var MAX_LEFT_DISTANCE = 255


//     constructor(_mainActivity: FlutterTelpoPlugin) {
//         mainActivity = _mainActivity
// //        ThermalPrinter(_mainActivity.context);
//     }


//     fun printText(details: MutableList<Map<String, Any>>) {
//         documentDetails = details;

//         if (LowBattery == true) {
//             MyHandler().sendMessage(MyHandler().obtainMessage(LOWBATTERY, 1, 0, null))
//         } else {
//             if (!nopaper) {
//                 MyHandler().sendMessage(MyHandler().obtainMessage(PRINTCONTENT, 1, 0, null))
//             } else {
// //                Toast.makeText(mainActivity, "NO PAPER AVAILABLE", Toast.LENGTH_LONG).show()
//             }
//         }
//     }

//     fun setAlignment(alignment: Int): Int {
//         when (alignment) {
//             0 -> { return ThermalPrinter.ALGIN_LEFT}
//             1 -> { return ThermalPrinter.ALGIN_MIDDLE}
//             2 -> { return  ThermalPrinter.ALGIN_RIGHT}
//             else -> { return  ThermalPrinter.ALGIN_LEFT}
//         }
//     }



//     inner class MyHandler : android.os.Handler() {
//         override fun handleMessage(msg: android.os.Message) {
//             when (msg.what) {
//                 NOPAPER -> {}
//                 NOPAPER -> {}
//                 LOWBATTERY -> {}
//                 NOBLACKBLOCK -> {}
//                 PRINTVERSION -> {}
//                 PRINTBARCODE -> {}
//                 PRINTBARCODE -> {}
//                 PRINTQRCODE -> {}
//                 PRINTQRCODE -> {}
//                 PRINTPAPERWALK -> {}
//                 PRINTCONTENT -> contentDocument()
//                 MAKER -> {}
//                 PRINTPICTURE -> {}
//                 CANCELPROMPT -> {}
//                 OVERHEAT -> {}
//             }
//         }
//     }

//     fun contentDocument() {
//         Handler(Looper.getMainLooper()).post {
//             try {
//                 ThermalPrinter.start(mainActivity.context)
//                 ThermalPrinter.reset()

//                 ThermalPrinter.setAlgin(textAlignment)
//                 ThermalPrinter.setLeftIndent(leftDistance)
//                 ThermalPrinter.setLineSpace(lineDistance)
//                 ThermalPrinter.setGray(printGray)

//                 for (row in documentDetails) {

//                     if(row["type"].toString() == "walkPaper"){
//                         ThermalPrinter.walkPaper(row["step"].toString().toIntOrNull() ?: 2)
//                     }

//                     if(row["type"].toString() == "qrcode"){
//                         val bitmap: Bitmap = CreateCode(row["text"].toString(), com.google.zxing.BarcodeFormat.QR_CODE, row["width"].toString().toIntOrNull() ?: 256, row["height"].toString().toIntOrNull() ?: 256)
//                         if (bitmap != null) {
//                             ThermalPrinter.setAlgin(setAlignment(1))
//                             ThermalPrinter.printLogo(bitmap)
//                         }
//                     }

//                     if(row["type"].toString() == "image"){
//                         val bitmap: Bitmap? = createImage(row["image_path"].toString(), row["width"].toString().toIntOrNull() ?: 256, row["height"].toString().toIntOrNull() ?: 256)
//                         if (bitmap != null) {
//                             ThermalPrinter.setAlgin(setAlignment(row["position"].toString().toIntOrNull() ?: 1))
//                             ThermalPrinter.printLogo(bitmap)
//                         }
//                     }

//                     if(row["type"].toString() == "text"){
//                         if(row["text"].toString() == "null") {
//                             ThermalPrinter.walkPaper(2)
//                         }
//                         else {
//                             if (row["fontSize"].toString().toIntOrNull() == 6) {
//                                 ThermalPrinter.setFontSize(64)
//                             } else if (row["fontSize"].toString().toIntOrNull() == 5) {
//                                 ThermalPrinter.setFontSize(54)
//                             } else if (row["fontSize"].toString().toIntOrNull() == 4) {
//                                 ThermalPrinter.setFontSize(44)
//                             } else if (row["fontSize"].toString().toIntOrNull() == 3) {
//                                 ThermalPrinter.setFontSize(34)
//                             } else if (row["fontSize"].toString().toIntOrNull() == 2) {
//                                 ThermalPrinter.setFontSize(24)
//                             } else {
//                                 ThermalPrinter.setFontSize(18)
//                             }

//                             ThermalPrinter.setAlgin(setAlignment(row["position"].toString().toIntOrNull() ?: 0))
//                             ThermalPrinter.addString(row["text"].toString())
//                             ThermalPrinter.printString()
//                         }
//                     }
//                 }
//             } catch (e: Exception) {
//                 e.printStackTrace()
//                 Result = e.toString()
//                 if (Result == "com.telpo.tps550.api.printer.NoPaperException") {
//                     nopaper = true
//                 } else if (Result == "com.telpo.tps550.api.printer.OverHeatException") {
//                     MyHandler().sendMessage(MyHandler().obtainMessage(OVERHEAT, 1, 0, null))
//                 } else {
//                     MyHandler().sendMessage(MyHandler().obtainMessage(PRINTERR, 1, 0, null))
//                 }
//             } finally {
//                 MyHandler().sendMessage(MyHandler().obtainMessage(CANCELPROMPT, 1, 0, null))
//                 if (nopaper) {
//                     MyHandler().sendMessage(MyHandler().obtainMessage(NOPAPER, 1, 0, null))
//                     nopaper = false
//                 } else {
//                     ThermalPrinter.stop(mainActivity.context)
//                 }

//             }
//         }
//     }

//     fun stop(){
//         ThermalPrinter.stop();
//     }

//     /**
//      *
//      * @param str
//      * @param type
//      * AZTEC, CODABAR, CODE_39, CODE_93, CODE_128, DATA_MATRIX,
//      * EAN_8, EAN_13, ITF, MAXICODE, PDF_417, QR_CODE, RSS_14,
//      * RSS_EXPANDED, UPC_A, UPC_E, UPC_EAN_EXTENSION;
//      * @param bmpWidth
//      * @param bmpHeight
//      */
//     @kotlin.jvm.Throws(WriterException::class)
//     fun CreateCode(str: String?, type: BarcodeFormat?, bmpWidth: Int, bmpHeight: Int): Bitmap {
//         val matrix: BitMatrix = MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight)
//         val width: Int = matrix.getWidth()
//         val height: Int = matrix.getHeight()
//         val pixels = IntArray(width * height)
//         for (y in 0 until height) {
//             for (x in 0 until width) {
//                 if (matrix.get(x, y)) {
//                     pixels[y * width + x] = -0x1000000
//                 } else {
//                     pixels[y * width + x] = -0x1
//                 }
//             }
//         }
//         val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//         bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
//         return bitmap
//     }


//     fun createImage(imageUrl: String, bmpWidth: Int, bmpHeight: Int): Bitmap? {
//         val assetPath: String = mainActivity.binding.getFlutterAssets().getAssetFilePathBySubpath(imageUrl)
//         val inStream: InputStream = mainActivity.binding.getApplicationContext().getAssets().open(assetPath)
//         val bitmap: Bitmap? = getResizedBitmap(BitmapFactory.decodeStream(inStream), bmpWidth, bmpHeight);
//         inStream.close();
//         return bitmap;
//     }

//     /**
//      * reduces the size of the image
//      * @param image
//      * @param maxSize
//      * @return
//      */
//     fun getResizedBitmap(image: Bitmap, width: Int, height: Int): Bitmap? {
//         return Bitmap.createScaledBitmap(image, width, height, true)
//     }
// }