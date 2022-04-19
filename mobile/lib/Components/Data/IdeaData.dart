import 'package:thebuzz/Components/Idea.dart';
import 'package:json_annotation/json_annotation.dart';
part 'IdeaData.g.dart';

@JsonSerializable()
class IdeaData{
  int? ideaId = 0;
  int? replyTo = 0;
  String? userId = "";
  String? userName = "";
  String? userAvatar = "";
  int? timestamp = 0;
  String? subject = "";
  String? content = "";
  String? attachment = "";
  int? numLikes = 0;
  int? numDislikes = 0;
  List<IdeaData>? comments = [];
  //IdeaData(this.ideaId,this.replyTo,this.userId,this.userName,this.userAvatar,this.timestamp,this.subject,this.content,this.attachment,this.numLikes,this.numDislikes,this.comments);
  IdeaData();
  factory IdeaData.fromJson(Map<String,dynamic> json) => _$IdeaDataFromJson(json);
  /*
  IdeaData.fromJson(Map json){
    this.ideaId = json["ideaId"];
    this.replyTo = json["replyTo"];
    this.userId = json["userId"];
    this.userName = json["userName"];
    this.userAvatar = json["userAvatar"];
    this.timestamp = json["timestamp"];
    this.subject = json["subject"];
    this.content = json["content"];
    this.attachment = json["attachment"];
    this.numLikes = json["numLikes"];
    this.numDislikes = json["numDislikes"];
    this.comments = json["comments"];
  }*/
}