// shadcn components use ResizeObserver
// and they can crash tests if ResizeObserver is not mocked
global.ResizeObserver = class ResizeObserver {
  constructor(callback) {
    this.callback = callback;
  }
  observe() { }
  unobserve() { }
  disconnect() { }
}
