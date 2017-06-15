import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { HotlistRefCode } from './hotlist-ref-code.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HotlistRefCodeService {

    private resourceUrl = 'api/hotlist-ref-codes';

    constructor(private http: Http) { }

    create(hotlistRefCode: HotlistRefCode): Observable<HotlistRefCode> {
        const copy = this.convert(hotlistRefCode);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(hotlistRefCode: HotlistRefCode): Observable<HotlistRefCode> {
        const copy = this.convert(hotlistRefCode);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<HotlistRefCode> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(hotlistRefCode: HotlistRefCode): HotlistRefCode {
        const copy: HotlistRefCode = Object.assign({}, hotlistRefCode);
        return copy;
    }
}
