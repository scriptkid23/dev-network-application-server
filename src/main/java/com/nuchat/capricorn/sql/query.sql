select messages.created_at, messages.message, users.id, messages.message_type, conversation.channel_id, users.avatar, users.email
from (messages inner join users on messages.user_id = users.id)
inner join conversation on messages.conversation_id = conversation.id
where
messages.created_at in (
select MAX(messages.created_at) as last_message_at
from messages inner join users on messages.user_id = users.id
where
  messages.conversation_id in
  (
    select participants.conversation_id
    from users inner join participants on users.id = participants.user_id
    where users.id = 4
  )
group by
messages.conversation_id
)
