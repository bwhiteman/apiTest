import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Hotlist } from './hotlist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HotlistService {

    private resourceUrl = 'api/hotlists';

    constructor(private http: Http) { }

    create(hotlist: Hotlist): Observable<Hotlist> {
        const copy = this.convert(hotlist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(hotlist: Hotlist): Observable<Hotlist> {
        const copy = this.convert(hotlist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Hotlist> {
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

    private convert(hotlist: Hotlist): Hotlist {
        const copy: Hotlist = Object.assign({}, hotlist);
        return copy;
    }
}
