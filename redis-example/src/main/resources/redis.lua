local num = redis.call('GET', KEYS[1]);

if not num then
    return 0;
else
    local res = num * ARGV[1];
    redis.call('SET',KEYS[1], res);
    return res;
end
