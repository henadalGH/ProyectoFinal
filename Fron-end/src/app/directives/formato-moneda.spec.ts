import { FormatoMoneda } from './formato-moneda';

describe('FormatoMoneda', () => {
  it('should create an instance', () => {
    const directive = new FormatoMoneda();
    expect(directive).toBeTruthy();
  });
});
