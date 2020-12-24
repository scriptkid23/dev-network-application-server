-- create function get list friend
DROP FUNCTION get_list_friend();
CREATE OR REPLACE FUNCTION get_list_friend()
  RETURNS TABLE (
	  first_name varchar,
	  last_name varchar,
	  avatar varchar,
	  bio varchar,
	  birthday timestamp,
	  email varchar,
	  gender boolean,
	  phone_number varchar
  ) AS
$func$
BEGIN
   RETURN QUERY
   SELECT user_contact.first_name, user_contact.last_name,
   users.avatar,users.bio,users.birthday, users.email,users.gender,
   users.phone_number
   FROM user_contact
   INNER JOIN users
   ON user_contact.contact_id = users.id
   WHERE user_id = 1;
END
$func$  LANGUAGE plpgsql;