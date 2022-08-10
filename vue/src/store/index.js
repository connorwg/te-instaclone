import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    active: 'home',
    icon: 'https://th.bing.com/th/id/As7xDOIkJIOgEcA480x360?&rs=1&pid=ImgDet',
    token: currentToken || '',
    user: currentUser || {},
    images: [{
      id: '1',
      userId: 'Prachi',
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03E9U67GNL-bd5c7423ccc1-512",
      description: 'photo description here',
      timeStamp: '',
      likes: '7',
      comments: ['blah  comments blah', 'comments blah', 'more comments']
    },
    {
      id: '2',
      userId: 'Connor',
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03B3TBG8HK-c6b48e687235-512",
      description: 'photo description here',
      timeStamp: '',
      likes: '9',
      comments: ['blah comments comments blah', 'comments', 'extra comments']
    }, {
      id: '3',
      userId: 'Andrew',
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03DK8YV9LZ-3d43d76e3219-512",
      description: 'photo description here',
      timeStamp: '',
      likes: '11',
      comments: ['blah blah comments comments', 'comments blah blah']
    }, {
      id: '4',
      userId: 'Dave',
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03B3T73DSR-9631f652a35e-512",
      description: 'photo description here',
      timeStamp: '',
      likes: '6',
      comments: ['blah comments blah', 'comments']
    },

    ]
  },
  mutations: {
    SET_TAB(state, active) {
      state.active = active;
    },
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_PHOTOS(state, data) {
      state.images = data;
    },
    SET_PHOTO(state,data) {
      let photoToUpdate = state.images.findIndex(photo => {
         return photo.id == data.id
      });
      state.images[photoToUpdate].likes= data.likes;
    
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    ADD_COMMENT(state, commentObj){
      state.images.forEach(image => {
        if(image.id === commentObj.post_id){
          image.comments.push(0, commentObj.comment);
        }
      });
    }
  }
})
