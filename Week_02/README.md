学习笔记
HashMap:
hashmap是一个键值对的集合，基于数组和链表实现
put方法：
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;//判断当前表是否为空，为空则初始化
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
            //根据当前 key 的 hashcode 定位到具体的桶中并判断是否为空，为空表明没有 Hash 冲突就直接在当前位置创建一个新桶即可。
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                //如果当前桶有Hash 冲突，就比较当前桶中的 key、key 的 hashcode 与写入的 key 是否相等
                e = p;
            else if (p instanceof TreeNode)
                //如果当前桶为红黑树，则按照红黑树的方式写入数据
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    //如果是个链表，就需要将当前的 key、value 封装成一个新节点写入到当前桶的后面
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // 接着判断当前链表的大小是否大于预设的阈值，大于时就要转换为红黑树
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))//如果在遍历过程中找到 key 相同时直接退出遍历。
                        break;
                    p = e;
                }
            }
            if (e != null) { // 如果 e != null 就相当于存在相同的 key,那就需要将值覆盖
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)//判断是否需要进行扩容
            resize();
        afterNodeInsertion(evict);
        return null;
    }

get方法：

final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {//如果桶为空则直接返回 null 
            if (first.hash == hash && // 否则判断桶的第一个位置的 key 是否为查询的 key，是就直接返回 value。
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {//如果第一个不匹配，则判断它的下一个是红黑树还是链表
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);//红黑树就按照树的查找方式返回值
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))//否则按照链表的方式遍历匹配返回值
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;




i