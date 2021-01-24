DROP FUNCTION get_list_friend;
CREATE OR REPLACE FUNCTION get_list_friend(userId int)
    RETURNS TABLE (
                      id int,
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

        SELECT
            users.id,
            user_contact.first_name, user_contact.last_name,
            users.avatar,users.bio,users.birthday, users.email,users.gender,
            users.phone_number
        FROM user_contact
        INNER JOIN users
        ON user_contact.contact_id = users.id
        WHERE user_id = userId;
END
$func$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_list_message_log(userId int)
	RETURNS TABLE(
		user_id int,
		avatar varchar,
		first_name_receiver varchar,
		last_name_receiver varchar,
		channel_id varchar,
		created_at timestamp,
		message varchar,
		sender_id int,
		first_name_sender varchar,
		last_name_sender varchar,
		message_type int,
		email varchar
	)AS
	$func$
	BEGIN

		CREATE TEMP TABLE TableLastMessage(
			created_at timestamp,
			message varchar,
			sender_id int,
			first_name varchar,
			last_name varchar,
			message_type int,
			channel_id varchar,
			email varchar

		);
		CREATE TEMP TABLE TableContacted(
			user_id int,
			avatar varchar,
			first_name varchar,
			last_name varchar,
			channel_id varchar
		);

		INSERT INTO TableLastMessage
		SELECT
		messages.created_at, messages.message, users.id AS sender_id,
		users.first_name,users.last_name,
		messages.message_type, conversation.channel_id, users.email
		FROM (messages INNER JOIN users ON messages.user_id = users.id)
		INNER JOIN conversation ON messages.conversation_id = conversation.id
		WHERE
		messages.created_at IN (
		SELECT MAX(messages.created_at) AS last_message_at
		FROM messages INNER JOIN users ON messages.user_id = users.id
		WHERE
		  messages.conversation_id IN
		  (
			SELECT participants.conversation_id
			FROM users INNER JOIN participants ON users.id = participants.user_id
			WHERE users.id = userId
		  )
		GROUP BY
		messages.conversation_id
		);

		INSERT INTO TableContacted
		SELECT participants.user_id, users.avatar, users.first_name, users.last_name,conversation.channel_id
		FROM (participants INNER JOIN conversation ON participants.conversation_id = conversation.id)
		INNER JOIN users ON users.id = participants.user_id
		INNER JOIN TableLastMessage ON TableLastMessage.channel_id = conversation.channel_id
		WHERE  participants.user_id != userId;

		RETURN QUERY
		SELECT TableContacted.user_id,
		TableContacted.avatar,
		TableContacted.first_name as first_name_receiver,
		TableContacted.last_name as last_name_receiver,
		TableContacted.channel_id ,
		TableLastMessage.created_at,
		TableLastMessage.message,
		TableLastMessage.sender_id,
		TableLastMessage.first_name as first_name_sender,
		TableLastMessage.last_name as last_name_sender,
		TableLastMessage.message_type,
		TableLastMessage.email

		FROM TableLastMessage
		INNER JOIN TableContacted ON TableLastMessage.channel_id = TableContacted.channel_id;

		DROP TABLE TableLastMessage;
		DROP TABLE TableContacted;
	END

$func$  LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION get_list_last_message_log(userId int)
    RETURNS 	TABLE(
                        id int,
                        created_at timestamp,
                        message varchar,
                        message_type int,
                        conversation_id int,
                        user_id int,
                        channel_id varchar,
                        title varchar,
                        avatar varchar,
                        bio varchar,
                        first_name varchar,
                        last_name varchar
                    )AS
$func$
BEGIN

    CREATE TEMP TABLE TableLastMessage(
          id int,
          created_at timestamp,
          guid varchar,
          message varchar,
          message_type int,
          conversation_id int,
          user_id int
    );
    INSERT INTO TableLastMessage
    SELECT * FROM messages WHERE
            messages.created_at in (SELECT MAX(messages.created_at)
                                    FROM participants
                                             INNER JOIN messages
                                                        ON participants.conversation_id = messages.conversation_id
                                    WHERE participants.user_id = userId
                                    GROUP BY (participants.conversation_id));
    RETURN QUERY
        SELECT
            TableLastMessage.id,
            TableLastMessage.created_at,
            TableLastMessage.message,
            TableLastMessage.message_type,
            TableLastMessage.conversation_id,
            TableLastMessage.user_id,
            conversation.channel_id,
            conversation.title,
            users.avatar,
            users.bio,
            users.first_name,
            users.last_name

        FROM
            (TableLastMessage inner join conversation on TableLastMessage.conversation_id = conversation.id)
                inner join users on TableLastMessage.user_id = users.id;
    DROP TABLE TableLastMessage;
END
$func$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_member_of_conversation(conversationId int)
    RETURNS TABLE(
                     id int,
                     avatar varchar,
                     email varchar,
                     first_name varchar,
                     last_name varchar,
                     is_present boolean
                 )
AS
$func$
BEGIN
    RETURN QUERY
        SELECT
            users.id,users.avatar,users.email, users.first_name,users.last_name,users.is_present
        FROM
            (conversation INNER JOIN participants ON conversation.id = participants.conversation_id)
                INNER JOIN users ON participants.user_id = users.id
        WHERE conversation.id = conversationId;
END
$func$  LANGUAGE plpgsql;