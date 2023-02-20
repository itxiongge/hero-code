local key = KEYS[1];
local threadId = ARGV[1];
local releaseTime = ARGV[2];

-- lockname不存在
if(redis.call('exists', key) == 0) then
    redis.call('hset', key, threadId, '1');
    redis.call('expire', key, releaseTime);
    return 1;
end;

-- 当前线程已id存在
if(redis.call('hexists', key, threadId) == 1) then
    local count = redis.call('hincrby', key, threadId, '1');
    -- 续期
    redis.call('expire', key, releaseTime);
    return count;
end;
return 0;
