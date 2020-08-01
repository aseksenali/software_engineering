/**
 * Metro configuration for React Native
 * https://github.com/facebook/react-native
 *
 * @format
 */
const {getDefaultConfig} = require('metro-config');

module.exports = (async () => {
  const {
    resolver: {sourceExts},
  } = await getDefaultConfig();
  return {
    transformer: {
      babelTransformerPath: 'react-native-css-transformer',
    },
    resolver: {
      sourceExts: [...sourceExts, 'css', 'jsx', 'js'],
      resolverMainFields: ['react-native', 'main']
    },
  };
})();
