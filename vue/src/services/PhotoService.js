import axios from 'axios';
import qs from 'qs';
export default {

  getPhotos() {
    return axios.get('/feed');
  },

  getPhotoById(currentPostId) {
    return axios.get(`/post/${currentPostId}`)
  },

  getPhotoByUserId(userId) {
    return axios.get(`/post/${userId}`)
  },

  addComment(commentObj) {
    return axios.post(`/post/${commentObj.postId}/create`, { params: { comment: commentObj.comment } });
  },

  addLike(newImage) {
    return axios.put(`/posts/${newImage.id}`, newImage);
  },

  unLike() {
    this.images.likes--;
    return axios.put(`/posts/${this.images.id}`, this.images.likes);
  },

  getUser(userId) {
    return axios.get(`/user/${userId}`);
  },

  addPicture(caption, picture) {
    const formData = new FormData();
    formData.append('desc', caption);
    formData.append('mpf', picture);
    return axios.post(`/post/create`, formData, { headers: { 'Content-Type': 'multipart/form-data' } });
  },//will return fields in an object - post_id, user_id, pictureLink, description ,timestamp

  addDisplayPicture(picture) {
    const formData = new FormData();
    formData.append('mpf', picture);
    return axios.post(`/user/profilePicture`, formData, { headers: { 'Content-Type': 'multipart/form-data' } });
  },//will return fields in an object - post_id, user_id, pictureLink, description ,timestamp

  deletePosts(postIdsToDelete) {
    return axios.delete(`/post/delete`, {
      params: { postIds: postIdsToDelete }, paramsSerializer: params => {
        return qs.stringify(params, {arrayFormat: 'repeat'})
      }
    });
  },

  addToFavorite(postId){
    return axios.post(`/post/${postId}/favorite`);
  },

  getFavorites(){
    return axios.get('/post/favorites');
  }
}
