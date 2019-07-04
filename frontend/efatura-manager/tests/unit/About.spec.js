import {createLocalVue, shallowMount} from "@vue/test-utils";
import Vuetify from "vuetify";
import About from "@/views/About";

describe('InvoiceTable.vue', function () {
    let wrapper;
    beforeEach(()=>{
        const localVue = createLocalVue();
        localVue.use(Vuetify);

        wrapper = shallowMount(About, {
            localVue
        });
    });

    it('renders a vue instance', () => {
        expect(shallowMount(About).isVueInstance()).toBe(true);
    });

    it('has an div', ()=>{
        expect(wrapper.contains('h1')).toBe(true);
    });
});