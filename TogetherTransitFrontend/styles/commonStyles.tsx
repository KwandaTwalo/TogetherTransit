// styles/commonStyles.ts
import { StyleSheet, Dimensions } from 'react-native';

const { width, height } = Dimensions.get('window');

export const commonStyles = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', alignItems: 'center' },

  // Blobs (background animations)
  blob1: {
    position: 'absolute',
    width: 250,
    height: 250,
    borderRadius: 125,
    backgroundColor: '#ff6b35',
    top: -60,
    left: -80,
    opacity: 0.25,
  },
  blob2: {
    position: 'absolute',
    width: 180,
    height: 180,
    borderRadius: 90,
    backgroundColor: '#f2f2f2',
    bottom: -40,
    right: -60,
    opacity: 0.3,
  },
  blob3: {
    position: 'absolute',
    width: 120,
    height: 120,
    borderRadius: 60,
    backgroundColor: '#ff9b6b',
    top: height / 2.5,
    left: width / 2.5,
    opacity: 0.25,
  },

  // Titles
  title: {
    color: '#1a1a1a',
    fontSize: width * 0.08,
    fontWeight: '900',
    marginBottom: 15,
    letterSpacing: 1,
    textShadowColor: '#ff6b35',
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 4,
  },
  slogan: {
    color: '#333',
    fontSize: width > 400 ? 22 : 18,
    marginBottom: 40,
    fontStyle: 'italic',
    opacity: 0.9,
    letterSpacing: 1,
  },

  // Buttons
  buttonPrimary: {
    backgroundColor: '#ff6b35',
    paddingVertical: 16,
    paddingHorizontal: 45,
    borderRadius: 35,
    marginBottom: 15,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.6,
    shadowRadius: 10,
    elevation: 8,
  },
  buttonSecondary: {
    backgroundColor: '#fff',
    paddingVertical: 16,
    paddingHorizontal: 45,
    borderRadius: 35,
    borderWidth: 2,
    borderColor: '#ff6b35',
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
    textTransform: 'uppercase',
    letterSpacing: 1.2,
  },
  buttonTextSecondary: {
    color: '#ff6b35',
    fontSize: 18,
    fontWeight: 'bold',
    textTransform: 'capitalize',
    letterSpacing: 1.2,
  },
});
