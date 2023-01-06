import 'package:thebuzz/Components/Idea.dart';
import 'package:json_annotation/json_annotation.dart';
part 'ProfileData.g.dart';

@JsonSerializable()
class ProfileData{
  String? userId = "";
  String note = "";
  String email = "";
  String name = "";
  String avatar = "";
  int companyRole = 0;
  //IdeaData(this.ideaId,this.replyTo,this.userId,this.userName,this.userAvatar,this.timestamp,this.subject,this.content,this.attachment,this.numLikes,this.numDislikes,this.comments);
  //ProfileData();
  ProfileData(this.email, this.name, this.avatar);
  factory ProfileData.fromJson(Map<String,dynamic> json) => _$ProfileDataFromJson(json);

}