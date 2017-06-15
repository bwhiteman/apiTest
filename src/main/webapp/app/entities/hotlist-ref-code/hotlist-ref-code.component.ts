import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { HotlistRefCode } from './hotlist-ref-code.model';
import { HotlistRefCodeService } from './hotlist-ref-code.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-hotlist-ref-code',
    templateUrl: './hotlist-ref-code.component.html'
})
export class HotlistRefCodeComponent implements OnInit, OnDestroy {
hotlistRefCodes: HotlistRefCode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hotlistRefCodeService: HotlistRefCodeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hotlistRefCodeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hotlistRefCodes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHotlistRefCodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HotlistRefCode) {
        return item.id;
    }
    registerChangeInHotlistRefCodes() {
        this.eventSubscriber = this.eventManager.subscribe('hotlistRefCodeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
