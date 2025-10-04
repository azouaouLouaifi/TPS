exports.transformObjectToArray=(obj) =>{
    const result = [];
    for (const key in obj) {
      if (Object.hasOwnProperty.call(obj, key)) {
        result.push(obj[key].role);
      }
    }
    return result;
  }

 