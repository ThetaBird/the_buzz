// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'IdeaData.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

IdeaData _$IdeaDataFromJson(Map<String, dynamic> json) => IdeaData()
  ..ideaId = json['ideaId'] as int?
  ..replyTo = json['replyTo'] as int?
  ..userId = json['userId'] as String?
  ..userName = json['userName'] as String?
  ..userAvatar = json['userAvatar'] as String?
  ..timestamp = json['timestamp'] as int?
  ..subject = json['subject'] as String?
  ..content = json['content'] as String?
  ..attachment = json['attachment'] as String?
  ..numLikes = json['numLikes'] as int?
  ..numDislikes = json['numDislikes'] as int?
  ..comments = (json['comments'] as List<dynamic>?)
      ?.map((e) => IdeaData.fromJson(e as Map<String, dynamic>))
      .toList();

Map<String, dynamic> _$IdeaDataToJson(IdeaData instance) => <String, dynamic>{
      'ideaId': instance.ideaId,
      'replyTo': instance.replyTo,
      'userId': instance.userId,
      'userName': instance.userName,
      'userAvatar': instance.userAvatar,
      'timestamp': instance.timestamp,
      'subject': instance.subject,
      'content': instance.content,
      'attachment': instance.attachment,
      'numLikes': instance.numLikes,
      'numDislikes': instance.numDislikes,
      'comments': instance.comments,
    };
