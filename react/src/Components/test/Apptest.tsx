import React from 'react';
import App from './App';
import '../path/to/spec_helper.js';

describe('app', () => {
  beforeEach(() => {
    spyOnRender(App).and.callThrough();
    ReactDOM.render(<App/>, root);
  });

  it('renders without crashing', () => {
    expect('.App').toExist();
    expect(App).toHaveBeenRenderedWithProps({});
  });
});