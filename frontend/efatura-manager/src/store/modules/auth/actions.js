import auth from '../../../api/auth';

const actions = {
    fetchSession({ commit }) {
        return auth.getSession().then((response) => {
            commit('setSession', response);
        });
    },
    saveSession({ commit }, payload) {
        return auth.setSession(payload).then((response) => {
            commit('setSession', response);
        });
    },
    destroySession({ commit }) {
        return auth.destroySession().then((response) => {
            commit('setSession', response);
        });
    }
};

export default actions;