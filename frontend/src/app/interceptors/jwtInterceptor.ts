import {HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {environment} from '../environments/environment.local';

export const jwtInterceptor: HttpInterceptorFn = (req, next,) => {
  let token = localStorage.getItem('access_token');
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${JSON.parse(token).token}`
      }
    });
  }
  return next(req);
}

export const apiHostInterceptor: HttpInterceptorFn = (req, next) => {
  const apiReq = req.clone({ url: `${environment.apiHost}${req.url}` });
  return next(apiReq);
};
