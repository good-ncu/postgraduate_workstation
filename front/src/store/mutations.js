// 修改token，并将token存入localStorage
export const CHANGE_LOGIN = (state, user)=>{
    state.Authorization = user.Authorization;
    localStorage.setItem('Authorization', user.Authorization);
};
