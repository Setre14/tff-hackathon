import {createRouter, createWebHistory} from 'vue-router'
import EventsView from "@/views/EventsView.vue";
import EventDetailsView from "@/views/EventDetailsView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'events',
            component: EventsView
        },
        {
            path: '/event/:name',
            name: 'event',
            component: EventDetailsView,
            params: true
        },
    ]
})

export default router
