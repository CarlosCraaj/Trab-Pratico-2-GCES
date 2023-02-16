window.onload = () => {
   localStorage.getItem('USER');
   const userName = localStorage.getItem('USER');
   userName
      ? (document.querySelector('.userMenuHello').innerText = `Olá, ${userName} !`)
      : (document.querySelector('.userMenuHello').innerText = 'Olá!');

   doc;
};
