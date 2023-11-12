import {createRouter, createWebHistory} from 'vue-router'
import EventsView from "@/views/EventsView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: EventsView
        },
    ]
})

export default router
