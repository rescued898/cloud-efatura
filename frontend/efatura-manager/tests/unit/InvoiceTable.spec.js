import {createLocalVue, shallowMount} from "@vue/test-utils";
import Vuetify from 'vuetify';
import InvoiceTable from '@/components/InvoiceTable';

describe('InvoiceTable.vue', function () {
    let wrapper;
    let totalInvoices = 12;
    beforeEach(()=>{
        const localVue = createLocalVue();
        localVue.use(Vuetify);

        wrapper = shallowMount(InvoiceTable, {
            localVue,
            propsData: {
                totalInvoices
            }
        });
    });

    it('renders a vue instance', () => {
        expect(shallowMount(InvoiceTable).isVueInstance()).toBe(true);
    });

    it('has an div', ()=>{
        expect(wrapper.contains('div')).toBe(true);
    });

    it('checks invoice count', ()=>{
        expect(wrapper.vm.totalInvoices).toBe(12);
    })
});