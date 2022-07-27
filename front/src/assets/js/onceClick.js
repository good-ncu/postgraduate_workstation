export default {
    install (Vue) {
        // 防止重复点击 在前端元素中添加 v-prevent-click属性
        Vue.directive('onceClick', {
            inserted (el, binding) {
                el.addEventListener('click', () => {
                    if (el.style.pointerEvents === '') {
                        // el.disabled = true;
                        el.style.pointerEvents = 'none';   // 防止a标签
                        setTimeout(() => {
                            el.style.pointerEvents = '';
                            //el.disabled = false;
                            // 这里设置的3秒不可重复点击
                        }, 3000);
                    }
                })
            }
        })
    }
}
