// mutations
const mutations = {
    setSession(state, payload) {
        if (typeof (payload) === 'object') {
            state.user = payload.user;
            state.token = payload.token;
        } else {
            state.user = {};
            state.token = '';
        }
    }
};

export default mutations;