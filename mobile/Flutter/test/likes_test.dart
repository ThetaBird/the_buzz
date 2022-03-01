// Import the test package and Counter class

import 'package:mobile/main.dart';
import 'package:test/test.dart';




void main() {
  test('Likes value should be incremented', () {
    final Userlikes = AddLikes();

    AddLikes.increment();

    expect(AddLikes.likes, 1);
  });
}