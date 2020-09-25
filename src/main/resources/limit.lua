
--说明：
--key：在redis中记录访问次数的index,在这里我们用method的名字加ip地址进行限制
--limit:  单ip对此method最多可以访问的次数
--length: 限制次数生效的时长
--原理：
--key不存在时，新建一个key,value设置为1，并设定过期时间
--如果key存在，看是否超过单位时间内允许访问的最高次数，
--如果超过，返回0，
--如果不超过，返回1
--说明：为什么使用lua脚本？
--redis上的lua脚本的执行是原子性的，不存在多个线程的并发问题，
--使用lua脚本能保证高并发时也不会出现超出流量限制


local key = KEYS[1]  -- 在redis中记录访问次数的index
local limit = tonumber(KEYS[2]) --单ip对此method最多可以访问的次数
local length = tonumber(KEYS[3]) --限制次数生效的时长
--redis.log(redis.LOG_NOTICE,' length: '..length)
local current = redis.call('GET', key)
if current == false then
    --redis.log(redis.LOG_NOTICE,key..' is nil ')
    redis.call('SET', key,1)
    redis.call('EXPIRE',key,length)
    --redis.log(redis.LOG_NOTICE,' set expire end')
    return '1'
else
    --redis.log(redis.LOG_NOTICE,key..' value: '..current)
    local num_current = tonumber(current)
    if num_current+1 > limit then
        return '0'
    else
        redis.call('INCRBY',key,1)
        return '1'
    end
end



