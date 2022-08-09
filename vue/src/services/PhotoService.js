import axios from 'axios';

export default {

  getPhotos() {
    return axios.get('/post');
  },

  addComment(newComment, userName) {
      return axios.put(`/posts/${this.images.id}`, newComment, userName);

  },

  addLike() {
      this.images.likes++;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  },

  unLike() {
      this.images.likes--;
      return axios.put(`/posts/${this.images.id}`, this.images.likes);
  }

  

}