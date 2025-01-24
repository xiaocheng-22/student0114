// src/router/index.js
import Vue from 'vue';
import Router from 'vue-router';
// 引入你的组件
import Home from '../components/Home.vue';

Vue.use(Router);

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/Student/',
        name: 'Student',
        component: () => import("../components/Student.vue") // 按需加载组件
    },
    {
        path: '/Student2/',
        name: 'Student2',
        component: () => import("../components/Student.vue") // 按需加载组件
    },
];

const router = new Router({
    mode: 'hash', // 可以选择'mode: "hash"'或'mode: "history"'
    routes,
});

export default router;