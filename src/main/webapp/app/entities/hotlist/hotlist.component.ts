import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Hotlist } from './hotlist.model';
import { HotlistService } from './hotlist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-hotlist',
    templateUrl: './hotlist.component.html'
})
export class HotlistComponent implements OnInit, OnDestroy {
hotlists: Hotlist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hotlistService: HotlistService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hotlistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hotlists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHotlists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Hotlist) {
        return item.id;
    }
    registerChangeInHotlists() {
        this.eventSubscriber = this.eventManager.subscribe('hotlistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
