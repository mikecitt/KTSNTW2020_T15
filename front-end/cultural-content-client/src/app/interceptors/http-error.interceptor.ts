import { SnackBarComponent } from './../core/snack-bar/snack-bar.component';
import { Injectable } from '@angular/core';
import { NewsComponent } from './../modules/news/news-component/news.component';
import {
HttpEvent,
HttpInterceptor,
HttpHandler,
HttpRequest,
HttpResponse,
HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    constructor(private snackBar: SnackBarComponent) {}
    
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
    .pipe(
        retry(1),
        catchError(err => {
           
            // let error =  typeof err.error !== "string" ? err.error : err.error.error;
            let errorMessage;
            if(typeof err.error === "string") errorMessage = err.error;
            else if(err.erorr == null) errorMessage = err.message;
            else errorMessage = err.error.error

            this.snackBar.openSnackBar(errorMessage,'','red-snackbar');
            return throwError(errorMessage);
        })
    )
    }
}