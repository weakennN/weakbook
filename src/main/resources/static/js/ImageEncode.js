function encodeImages(imageFiles) {
    let promises = [];
    for (let i = 0; i < imageFiles.length; i++) {
        promises.push(readAsBase64(imageFiles[i]));
    }

    return promises;
}

function readAsBase64(file) {
    return new Promise(function (resolve, reject) {
        let fr = new FileReader();

        fr.onload = function () {
            resolve(fr.result);
        };

        fr.onerror = function () {
            reject(fr);
        };

        fr.readAsDataURL(file);
    });
}