import {createLocalVue, shallowMount} from "@vue/test-utils";
import Vuetify from "vuetify";
import Home from "@/views/Home";
import InvoiceTable from "@/components/InvoiceTable";

describe('InvoiceTable.vue', function () {
    let wrapper;
    beforeEach(()=>{
        const localVue = createLocalVue();
        localVue.use(Vuetify);

        wrapper = shallowMount(Home, {
            localVue
        });
    });

    it('renders a vue instance', () => {
        expect(shallowMount(Home).isVueInstance()).toBe(true);
    });

    it('check if child InvoiceTable exists', ()=>{
        expect(wrapper.contains(InvoiceTable)).toBe(true);
    });
});