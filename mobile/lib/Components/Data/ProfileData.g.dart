// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'ProfileData.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProfileData _$ProfileDataFromJson(Map<String, dynamic> json) => ProfileData(
      json['email'] as String,
      json['name'] as String,
      json['avatar'] as String,
    )
      ..userId = json['userId'] as String
      ..note = json['note'] as String
      ..companyRole = json['companyRole'] as int;

Map<String, dynamic> _$ProfileDataToJson(ProfileData instance) =>
    <String, dynamic>{
      'userId': instance.userId,
      'note': instance.note,
      'email': instance.email,
      'name': instance.name,
      'avatar': instance.avatar,
      'companyRole': instance.companyRole,
    };
