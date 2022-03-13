class PrintRow {
  String? text;
  int?  fontSize;
  int?  position;

  PrintRow({this.text, this.fontSize, this.position});

  PrintRow.fromJson(Map<String, dynamic> json) {
    text = json['text'];
    fontSize = json['fontSize'];
    position = json['position'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['type'] = "text";
    data['text'] = text;
    data['fontSize'] = fontSize;
    data['position'] = position;
    return data;
  }
}

class WalkPaper {
  int? step;

  WalkPaper({this.step});

  WalkPaper.fromJson(Map<String, dynamic> json) {
    step = json['step'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['type'] = "walkPaper";
    data['step'] = step;
    return data;
  }
}

class PrintQRCode {
  String? text;
  int?  width;
  int?  height;
  int?  position;

  PrintQRCode({this.text, this.width, this.height, this.position});

  PrintQRCode.fromJson(Map<String, dynamic> json) {
    text = json['text'];
    width = json['width'];
    height = json['height'];
    position = json['position'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['type'] = "qrcode";
    data['text'] = text;
    data['width'] = width;
    data['height'] = height;
    data['position'] = position;
    return data;
  }
}

class PrintAssetImage {
  String? imagePath;
  int?  width;
  int?  height;
  int?  position;

  PrintAssetImage({this.imagePath, this.width, this.height, this.position});

  PrintAssetImage.fromJson(Map<String, dynamic> json) {
    imagePath = json['image_path'];
    width = json['width'];
    height = json['height'];
    position = json['position'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {};
    data['type'] = "image";
    data['image_path'] = imagePath;
    data['width'] = width;
    data['height'] = height;
    data['position'] = position;
    return data;
  }
}



